# All positions are in the ned coordinate system

std_msgs/Header header

geographic_msgs/GeoPoint ship_geopoint
geometry_msgs/Pose map2ship

geographic_msgs/GeoPoint usbl_geopoint
geometry_msgs/Pose map2usbl
geometry_msgs/Point ship2usbl
float64 range
float64 azimuth
float64 elevation
int32 usbl_reliability_level