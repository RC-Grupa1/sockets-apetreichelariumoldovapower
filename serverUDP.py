import socket

# --- Configuration ---
# (Fixed Comment) Binding specifically to your Tailscale IP, not '0.0.0.0'
HOST = '100.65.183.62'  
PORT = 12345      # Make sure your colleague uses this UDP port in Java

def start_udp_chat_server():
    # 1. Create a UDP socket (SOCK_DGRAM means UDP)
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as server_socket:
        
        # 2. Bind the socket to the address and port
        server_socket.bind((HOST, PORT))
        print(f"UDP Server is up! Listening on port {PORT}...")
        print("Waiting for the first message from your colleague to know their address...")

        # Chat Loop
        while True:
            # 3. Receive data. 'recvfrom' returns both the data AND the sender's address
            data, addr = server_socket.recvfrom(1024)
            
            # Decode the byte data into a string
            # ADDED .strip() to clean up any invisible characters/newlines from Java
            client_message = data.decode('utf-8').strip() 
            print(f"\n--- Message received from {addr} ---")
            print(f"Colleague: {client_message}")

            # 4. Send a response directly to the address we just received from
            my_message = input("You: ")
            
            # THE FIX: Added the '\n' newline character before encoding!
            server_socket.sendto((my_message + '\n').encode('utf-8'), addr)

if __name__ == "__main__":
    start_udp_chat_server()