# Project Overview

This project contains 2 services:

## 1. reader-service

- **Port:** 8081

## 2. writer-service

- **Port:** 8082

## Text Files

### 1. input_data.txt

- **Purpose:** Data will continuously come into this file. For testing purposes, run both applications and continuously add data to it.

### 2. output_data.txt

- **Purpose:** Data will be stored in this file. Initially, remove all the data the first time.

### 3. last_position.txt

- **Purpose:** This file contains how many lines have been read so far. This helps prevent duplicate data when restarting the service. Initially, set its value to zero.

### 4. report.txt

- **Purpose:** Contains report information accessible via `localhost:8082/report`.

## Usage

1. **Start Services:**

   - Run `reader-service` on port 8081.
   - Run `writer-service` on port 8082.

2. **Data Flow:**

   - Continuously add data to `input_data.txt`.
   - `reader-service` reads this data and sends it to `writer-service`.
   - `writer-service` stores processed data in `output_data.txt`.

3. **Reporting:**
   - Access the report at `localhost:8082/report` to view processed data statistics.

## Notes

- Ensure to initialize `output_data.txt` and `last_position.txt` appropriately before starting the services for accurate data processing.
