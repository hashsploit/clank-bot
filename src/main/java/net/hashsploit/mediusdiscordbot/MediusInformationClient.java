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

import net.hashsploit.mediusdiscordbot.proto.MediusStructures.PlayersReq;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.TableData;


public class MediusInformationClient{

    // private final MediusInformationStub asyncStub;
    private final MediusInformationBlockingStub blockingStub;
    private final ManagedChannel grpcChannel;

    private final String name;
	private final String description;
	private final String address;
	private final int port;
	private final String MISToken;
    
    public MediusInformationClient(final String name, final String description, final String address, final int port, final String MISToken) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.port = port;
		this.MISToken = MISToken;

        this.grpcChannel = ManagedChannelBuilder.forTarget(String.format("%s:%s", address, Integer.toString(port))).usePlaintext().build();
        this.blockingStub = MediusInformationGrpc.newBlockingStub(this.grpcChannel);
	}

    /**
	 * Get the MIS name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the MIS description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the MIS address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Get the MIS server port.
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Get the MIS token.
	 * @return
	 */
	public String getMISToken() {
		return MISToken;
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
	
	public TableData GetPlayers(PlayersReq req){
        TableData grpcRes = null;

        try{
            grpcRes = this.blockingStub.getPlayers(req);
        } catch(Exception e){
            e.printStackTrace();
        }

        return grpcRes;
    }


}