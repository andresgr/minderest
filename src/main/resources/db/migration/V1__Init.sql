CREATE TABLE Client(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version bigint(20) NOT NULL,
  client_id varchar(36) NOT NULL,
  client_name varchar(200) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY Client__clientId_uidx (client_id)
);

CREATE TABLE Product(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version bigint(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Client_Product(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version bigint(20) NOT NULL,
  client_product_name varchar(255) DEFAULT NULL,
  client bigint(20) DEFAULT NULL,
  product bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ClientProduct__clientProductName_client__uidx (client_product_name, client),
  UNIQUE KEY ClientProduct__client_product__uidx (client, product),
  CONSTRAINT ClientProduct_client_fk FOREIGN KEY (client) REFERENCES Client (id),
  CONSTRAINT ClientProduct_product_fk FOREIGN KEY (product) REFERENCES Product (id)
);
