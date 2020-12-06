package net.hashsploit.mediusdiscordbot;

import java.util.HashMap;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.hashsploit.mediusdiscordbot.proto.MediusInformationGrpc;
import net.hashsploit.mediusdiscordbot.proto.MediusInformationGrpc.MediusInformationBlockingStub;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusListing;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusRes;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.StatusListing;


public class MediusInformationClient{

    // private final MediusInformationStub asyncStub;
    private final MediusInformationBlockingStub blockingStub;
    private final ManagedChannel grpcChannel;

    private final String name;
	private final String description;
	private final String address;
	private final int port;
	private final String token;
	private final int color;
	private String staticStatus;
    private final HashMap<String,String> commandIcons;
    
    public MediusInformationClient(final String name, final String description, final String address, final int port, final String token, final int color, final String staticStatus ,final HashMap<String, String> commandIcons) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.port = port;
		this.token = token;
		this.color = color;
		this.staticStatus = staticStatus;
        this.commandIcons = commandIcons;

        this.grpcChannel = ManagedChannelBuilder.forTarget(String.format("%s:%s", address, Integer.toString(port))).usePlaintext().build();
        this.blockingStub = MediusInformationGrpc.newBlockingStub(this.grpcChannel);
	}

    /**
	 * Get the Medius JQM server name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Medius JQM server description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the Medius JQM server address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Get the Medius JQM server port.
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get the Medius JQM server token.
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Get the server color.
	 * @return
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Get the Medius JQM static status message.
	 */
	public String getStaticStatus(){
		return staticStatus;
	}
	
	/**
	 * Get the Medius JQM server command icons.
	 */
	public HashMap<String, String> getCommandIcons(){
		return commandIcons;
    }

    //
    //
    //  MediusInformationm service GRPC client calls
    //
    //

    public StatusRes GetStatus(StatusReq req){
        StatusRes grpcRes = null;

        try{
            grpcRes = this.blockingStub.getStatus(req);
        } catch(Exception e){
            e.printStackTrace();
        }

        return grpcRes;
    }


}