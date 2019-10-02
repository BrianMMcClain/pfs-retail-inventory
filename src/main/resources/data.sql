DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS product_tag;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price REAL
);

CREATE TABLE tag (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE product_tag (
    products_id INT,
    tags_id INT
);


INSERT INTO product (id, name, price) VALUES 
    (1, 'Football', 15.99),
    (2, 'Baseball', 9.99),
    (3, 'History of Football', 7.99),
    (4, 'Football Jersey', 99.99),
    (5, 'Baseball Jersey', 99.99),
    (6, 'Thinking Architecturally', 9.99),
    (7, 'Modernizing .NET Applications', 9.99),
    (8, 'Cloud-Native Java', 14.99),
    (9, 'Building Microservices with ASP.NET Core', 17.99),
    (10, 'Spring Framework Foundations', 19.99);

INSERT INTO tag (id, name) VALUES
    (1, 'Football'),
    (2, 'Baseball'),
    (3, 'Book'),
    (4, 'Programming'),
    (5, '.NET'),
    (6, 'Spring');

INSERT INTO product_tag (products_id, tags_id) VALUES
    (1, 1),
    (2, 2),
    (3, 1), (3, 3),
    (4, 1),
    (5, 2),
    (6, 3), (6, 4),
    (7, 3), (7, 4), (7, 5),
    (8, 3), (8, 4), (8, 6),
    (9, 3), (9, 4), (9, 5),
    (10, 3), (10, 4), (10, 6);