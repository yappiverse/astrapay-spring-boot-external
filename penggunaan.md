# Cara Menjalankan Project

Sebelum mulai, pastikan beberapa tools ini sudah terpasang:

- Maven
- JDK 11
- Angular CLI

## Menjalankan backend

```bash
mvn spring-boot:run
```

## Menjalankan frontend

```bash
cd frontend
ng serve
```

## Menjalankan unit test backend

```bash
mvn test
```

# Screenshot

## API testing

### 1. Get All Notes
![Get All Notes](screenshots/1.jpeg)

### 2. Create Note - Valid
![Create Note - Valid](screenshots/2.jpeg)

### 3. Create Note - Empty Title (400)
![Create Note - Empty Title (400)](screenshots/3.jpeg)

### 4. Create Note - Empty Content (400)
![Create Note - Empty Content (400)](screenshots/4.jpeg)

### 5. Create Note - Duplicate Title (409)
![Create Note - Duplicate Title (409)](screenshots/5.jpeg)

### 6. Delete Note by ID - Valid
![Delete Note by ID - Valid](screenshots/6.jpeg)

### 7. Delete Note by ID - Not Found (404)
![Delete Note by ID - Not Found (404)](screenshots/7.jpeg)

### 8. Delete Note by Title - Valid
![Delete Note by Title - Valid](screenshots/8.jpeg)

### 9. Delete Note by Title - Not Found (404)
![Delete Note by Title - Not Found (404)](screenshots/9.jpeg)

## Web

### 10. Tampilan Web 1
![Tampilan Web 1](screenshots/10.jpeg)

### 11. Tampilan Web 2
![Tampilan Web 2](screenshots/11.jpeg)

## Unit testing

### 12. Hasil Unit Testing
![Hasil Unit Testing](screenshots/12.jpeg)