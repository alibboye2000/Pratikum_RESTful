/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pratikum_RESTful.demo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lenovo
 */
@ControllerAdvice
@RestController
public class ProductServiceController {
    private static final Map<String, Product> productRepo = new HashMap<>();
   static {
      Product honey = new Product();
      honey.setId("1");
      honey.setName("Honey");
      honey.setqty("2000");
      honey.setprice("20000");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.setId("2");
      almond.setName("Almond");
      almond.setqty("1000");
      almond.setprice("3000");
      productRepo.put(almond.getId(), almond);
   }

   
   @ExceptionHandler(value = ProductNotFoundException.class)
   public ResponseEntity<Object> exception(ProductNotFoundException exception) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
   }
   
    //Method GET
   @RequestMapping(value = "/products")
   public ResponseEntity<Object> getProduct() {
      return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
   }
   
    //Method POST
    // Create Condition, where HashMap(you can also use MAP) productRepo
    // get the key object ID in Product
        
    // if productRepo is equal to id value then return Id cannot be equal
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@RequestBody Product product) {
      if(productRepo.containsKey(product.getId())){ 
            return new ResponseEntity<>("ID Product Cannot be the Same, please check again", HttpStatus.OK);
        }
      // If it turns out that the productRepo id is not the same then create is successful
      else{
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created Successfully", HttpStatus.CREATED);
        }
   }
   
   //Method PUT
   
     @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
     public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
        
         // Creates a condition in the update or PUT section
        
         // if there is no productRepo with the id value set then the data cannot be updated
         // because it can't find the id you're looking for
         if(!productRepo. containsKey(id)){
             return new ResponseEntity<>("Product Not Found, Please check again", HttpStatus.NOT_FOUND);
         }
         // while the productRepo id value is there and the same so the data can be updated
         // because the id is in the hashmap
         else{
             productRepo. remove(id);
             product. setId(id);
             productRepo. put(id, product);
             return new ResponseEntity<>("Product is updated Successfully",HttpStatus.OK);
         }
     }
   
    //Method DELETE
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<Object> delete(@PathVariable("id") String id) { 
      productRepo.remove(id);
      return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
   }
}