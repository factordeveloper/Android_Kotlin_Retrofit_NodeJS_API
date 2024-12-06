from fastapi import APIRouter, HTTPException
from schemas.heartbeat import HeartbeatSchema
from database.connection import heartbeats_collection

router = APIRouter(prefix="/heartbeat", tags=["Heartbeat"])

@router.get("/")
async def get_all_heartbeats():
    heartbeats = []
    async for heartbeat in heartbeats_collection.find():
        heartbeat["_id"] = str(heartbeat["_id"])  # Convertir ObjectId a string
        heartbeats.append(heartbeat)
    return {"heartbeats": heartbeats}


@router.post("/")
async def receive_heartbeat(heartbeat: HeartbeatSchema):
    result = await heartbeats_collection.insert_one(heartbeat.dict())
    if result.inserted_id:
        return {"message": "Heartbeat received", "id": str(result.inserted_id)}
    raise HTTPException(status_code=500, detail="Failed to store heartbeat")