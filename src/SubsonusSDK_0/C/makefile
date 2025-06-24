all: static_example dynamic_example
ifeq ($(OS),Windows_NT)
FLAGS :=  -lws2_32
endif
static_example: lib/an_packet_protocol.c lib/an_packet_protocol.h lib/data_dump.h lib/subsonus_packets.c lib/subsonus_packets.h Static/static_example.c
	gcc -pedantic -std=c99 -Wall -o static_example lib/an_packet_protocol.c lib/subsonus_packets.c Static/static_example.c
	
dynamic_example: lib/an_packet_protocol.c lib/an_packet_protocol.h lib/data_dump.h lib/subsonus_packets.c lib/subsonus_packets.h Dynamic/dynamic_example.c
	gcc -pedantic -std=c99 -Wall -o dynamic_example lib/an_packet_protocol.c lib/subsonus_packets.c Dynamic/dynamic_example.c $(FLAGS)
	
clean:
	rm static_example
	rm dynamic_example
