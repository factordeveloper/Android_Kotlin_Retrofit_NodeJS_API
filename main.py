from fastapi import FastAPI
from routes import heartbeat

app = FastAPI()

# Registrar rutas
app.include_router(heartbeat.router)

@app.get("/")
async def root():
    return {"message": "Telematics API is running"}