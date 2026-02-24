# File Encryption Tool
A full-stack web application that **encrypts and decrypts user-requested files** using **AES (Advanced Encryption Standard)**.  
The application features a React-based frontend and a Spring Boot backend, allowing users to securely upload files, apply password-based encryption, and download the resulting output.

## How It Works
- The user selects a file and provides a password through the frontend interface.
- The file and password are sent to the backend via REST API.
- Backend encrypts or decrypts the file using AES.
- Processed file is then returned to the frontend available to download with **.enc or .dec** extension, depending on the operation performed.
- When decrypting a file, the password must match the original password used during encryption.

## Project Structure
```
file-encryption-tool/
├── backend/
└── frontend/
```



