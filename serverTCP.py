import socket

# --- Configuration ---
# Binding to '0.0.0.0' tells the server to listen on all available network interfaces,
# including your Tailscale network interface. 
HOST = '100.65.183.62'  
PORT = 12345        # Make sure your colleague uses this exact port in Java

def start_chat_server():
    # 1. Create a TCP socket (SOCK_STREAM means TCP)
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
        
        # 2 & 3. Bind the socket to the address and port, and Listen for connections
        server_socket.bind((HOST, PORT))
        server_socket.listen()
        print(f"Server is up! Tell your colleague to connect to your Tailscale IP on port {PORT}...")

        # 4. Accept the incoming connection from the Java client
        conn, addr = server_socket.accept()
        with conn:
            print(f"--- Client connected from {addr} ---")
            
            # Chat Loop
            while True:
                # 5. Receive data from the client (max 1024 bytes per message)
                data = conn.recv(1024)
                if not data:
                    print("--- Client disconnected ---")
                    break 
                
                # Decode the byte data into a string
                client_message = data.decode('utf-8')
                
                print(f"Client: {client_message}")

                # 6. Send a response back to the client
                my_message = input("You: ")
                conn.sendall((my_message + '\n').encode('utf-8'))
                
                # 7. The connection will automatically Close when the 'with' block ends

if __name__ == "__main__":
    start_chat_server()