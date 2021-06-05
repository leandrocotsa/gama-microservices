package com.thesis.gamamicroservices.inventoryservice.service;

import com.thesis.gamamicroservices.inventoryservice.dto.OrderForStockCheck;
import com.thesis.gamamicroservices.inventoryservice.dto.WarehouseSetDTO;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.InventoryUpdatedMessage;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.OrderCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.WarehouseCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.WarehouseDeletedMessage;
import com.thesis.gamamicroservices.inventoryservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.inventoryservice.model.Inventory;
import com.thesis.gamamicroservices.inventoryservice.model.Warehouse;
import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.inventoryservice.repository.InventoryRepository;
import com.thesis.gamamicroservices.inventoryservice.repository.ProductReplicaRepository;
import com.thesis.gamamicroservices.inventoryservice.repository.WarehouseRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductReplicaRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, WarehouseRepository warehouseRepository, ProductReplicaRepository productRepository, RabbitTemplate rabbitTemplate, @Qualifier("inventoryWarehouseExchange")DirectExchange exchange) {
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }


    public List<Inventory> findInventoriesByWarehouse(int warehouseID) {
        return inventoryRepository.findAllByWarehouseId(warehouseID);
    }

    public ProductReplica getProductById(int id) throws NoDataFoundException {
        Optional<ProductReplica> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NoDataFoundException("There's no product with id " + id);
        }
    }


    public void addStock(int productID, int warehouseID, int qty) throws NoDataFoundException {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndWarehouseId(productID, warehouseID);
        if (inventory.isPresent()) {
            inventory.get().setStockAmount(inventory.get().getStockAmount() + qty);
            inventoryRepository.save(inventory.get());
            rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new InventoryUpdatedMessage(inventory.get()));
        } else {
            try {
                Optional<Warehouse> w = warehouseRepository.findById(warehouseID);
                if (w.isPresent()) {
                    Inventory newInv = new Inventory(w.get(), this.getProductById(productID), qty);
                    inventoryRepository.save(newInv);
                    rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new InventoryUpdatedMessage(newInv));
                    //tambem devia fazer product.setInventories e colocar este novo e fazer save do produto
                    //alias, como é cascade ate bastava guardar só aí que o inventory tambem ia ser persistido
                    //mas como o inventory é o owning side funciona, mas se tiver bugs mudo
                } else {
                    throw new NoDataFoundException("There's no warehouse with id" + warehouseID);
                }
            } catch (NoDataFoundException e) {
                throw e;
            }
        }
    }

    public void editStock(int productID, int warehouseID, int qty) throws NoDataFoundException {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndWarehouseId(productID, warehouseID);
        if (inventory.isPresent()) {
            inventory.get().setStockAmount(qty);
            inventoryRepository.save(inventory.get());
            rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new InventoryUpdatedMessage(inventory.get()));
        } else {
            throw new NoDataFoundException("There's no inventory for Product with id " + productID + " and wahrehouse with id" + warehouseID);
        }
    }


    public void reserveStock(OrderCreatedMessage order) throws NoStockException, NoDataFoundException {
        //loop a ver se todos têm stock e outro loop a retirar um e guardar

        for (Map.Entry<Integer, Integer> entry : order.getProducts_qty().entrySet()) {

            if (this.getProductById(entry.getKey()).getStockAmount() < entry.getValue()) {
                throw new NoStockException("Not that much stock available for that product");
            }
        }
        for (Map.Entry<Integer, Integer> entry : order.getProducts_qty().entrySet()) {
            int qtyLeft = entry.getValue();
            for (int y = 0; y < getProductById(entry.getKey()).getInventories().size(); ++y) { //teve que ser loop assim pq com iterator na podia modificar elemento enquanto iterava
                if (qtyLeft == 0) {
                    return;
                }
                Inventory i = getProductById(entry.getKey()).getInventories().get(y);
                if (qtyLeft <= i.getStockAmount()) {
                    i.setStockAmount(i.getStockAmount() - qtyLeft);
                    qtyLeft = 0;
                } else {
                    qtyLeft = qtyLeft - i.getStockAmount();
                    i.setStockAmount(0);
                    //devia poder criar notificação para os admins, com scheduled jobs que verifica stocks de x em x tempo?
                    //com um thread constantemente a verificar se stock=0?
                }
                this.inventoryRepository.save(i);
                rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new InventoryUpdatedMessage(i));
                //tenho que por numa string na order de quais armazens stock foi retirado
            }

        }

    }


    public void createWarehouse(WarehouseSetDTO warehouseSetDTO) throws AlreadyExistsException {

        Optional<Warehouse> existingWarehouse = this.warehouseRepository.findByName(warehouseSetDTO.getName());
        if (existingWarehouse.isEmpty()) {
            Warehouse warehouse = new Warehouse((warehouseSetDTO));
            warehouseRepository.save(warehouse);
            rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new WarehouseCreatedMessage(warehouse));
        } else {
            throw new AlreadyExistsException("There's a Warehouse with that name");
        }
    }


    public void deleteWarehouse(int id) throws NoDataFoundException {
        if (warehouseRepository.existsById(id)) { //evita que tenha de fazer um fetch extra
            warehouseRepository.deleteById(id);
            rabbitTemplate.convertAndSend(exchange.getName(), "inventory", new WarehouseDeletedMessage(id));
        } else {
            throw new NoDataFoundException("There's no Warehouse with that id");
        }
    }

    public void editWarehouse(Map<String, Object> updates, int id) throws NoDataFoundException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if (warehouse.isPresent()) {
            try {
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on manager and set it to value v
                    Field field = ReflectionUtils.findField(Warehouse.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, warehouse.get(), v);
                });
                warehouseRepository.save(warehouse.get());
            } catch (Exception e) {
                throw new NoDataFoundException("Invalid arguments");
            }
        } else {
            throw new NoDataFoundException("There's no Warehouse with that id");
        }

    }
}
