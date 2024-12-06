from motor.motor_asyncio import AsyncIOMotorClient
from config import MONGO_URI, DB_NAME, COLLECTION_NAME

client = AsyncIOMotorClient(MONGO_URI)
db = client[DB_NAME]
heartbeats_collection = db[COLLECTION_NAME]