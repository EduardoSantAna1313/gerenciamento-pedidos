#!/bin/bash
psql -U postgres -d orders -c "CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";"