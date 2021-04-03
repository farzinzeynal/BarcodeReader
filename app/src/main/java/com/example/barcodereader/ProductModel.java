package com.example.barcodereader;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel
{
    long id;
    String name;
    String price;
    String barcode;

}
