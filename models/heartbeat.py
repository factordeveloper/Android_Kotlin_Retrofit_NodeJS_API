from pydantic import BaseModel, Field
from typing import List, Optional

class RelativePosition(BaseModel):
    distance: str
    unit_of_measure: str
    direction: str
    city: str
    state_code: str
    country_code: str

class Location(BaseModel):
    latitude: float
    longitude: float
    description: str
    country_code: str
    state_code: str
    relative_position: RelativePosition

class IdlePeriod(BaseModel):
    duration: str
    latitude: float
    longitude: float
    start_time: str

class Gps(BaseModel):
    distance_diff: float
    total_distance: float

class Attributes(BaseModel):
    event: str
    logged_at: str
    heartbeat_id: str
    speed: float
    odometer: float
    rpm: int
    engine_hours: float
    wheels_in_motion: bool
    gps: Gps
    location: Location
    idle_periods: List[IdlePeriod]

class Data(BaseModel):
    type: str
    id: str
    attributes: Attributes

class Meta(BaseModel):
    message_id: str
    consumer_version: str
    origin_version: str
    timestamp: str

class Heartbeat(BaseModel):
    data: Data
    meta: Meta
