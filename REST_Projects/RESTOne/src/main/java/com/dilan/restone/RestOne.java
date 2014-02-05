package com.dilan.restone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/hello")
public class RestOne {

	@GET
	@Path("/{param}")
	public Response getMessageWithParam(@PathParam("param") String msg) {

		String output = "Jersey say getMessageWithParam called : " + msg;

		return Response.status(200).entity(output).build();

	}

	@GET
	@Path("/dilan/{param}")
	public String getMessageWithPathAndParam(@PathParam("param") String msg) {

		String output = "Dilan say getMessageWithPathAndParam called : " + msg;

		return output;

	}

	@GET
	@Path("/dilan1")
	public String getMessageWithQueryPAram(@QueryParam("name") String msg) {

		String output = "Dilan say getMessageWithQueryPAram called name : "
				+ msg;

		return output;

	}

	@GET
	@Path("/dilan2")
	public String getMessageWithContext(@Context UriInfo info) {

		String msg = info.getQueryParameters().getFirst("name");
		String output = "Dilan say getMessageWithContext called name : " + msg;

		return output;

	}

	@GET
	@Path("/dilan3")
	public String getMessageWithDefaultValue(
			@DefaultValue("Default") @QueryParam("name") String msg) {

		String output = "Dilan say getMessageWithDefaultValue called name : "
				+ msg;

		return output;

	}

	@GET
	@Path("/dilan4")
	public String getMessageWithMatrixParam(@MatrixParam("name") String msg) {

		String output = "Dilan say getMessageWithDefaultValue name : " + msg;

		return output;

	}

	@POST
	@Path("/dilan5")
	public String getMessageWithFormQury(@FormParam("name") String msg) {

		String output = "Dilan say getMessageWithFormQury name : " + msg;

		return output;

	}

	@GET
	@Path("/dilan6")
	public String getHeaderInfoWithHeaderParam(
			@HeaderParam("user-agent") String msg) {

		String output = "Dilan say getHeaderInfoWithHeaderParam user-agent : "
				+ msg;

		return output;

	}

	@GET
	@Path("/dilan7")
	public String getHeaderInfoWithContext(@Context HttpHeaders headers) {

		String msg = headers.getRequestHeader("user-agent").get(0);

		String output = "Dilan say getHeaderInfoWithContext user-agent : "
				+ msg;

		return output;

	}

	@GET
	@Path("/dilan8")
	public String getAllHeaderInfoWithContext(@Context HttpHeaders headers) {

		String output = "Dilan say getHeaderInfoWithContext user-agent : ";

		Set<String> msg = headers.getRequestHeaders().keySet();

		for (String s : msg) {
			output = output + " : " + s;
		}

		return output;

	}

	@GET
	@Path("/dilan9/file")
	@Produces("text/plain")
	public Response getFileDownload() {

		String FILE_PATH = "C:\\dev\\struts2_tutorial.pdf";
		File file = new File(FILE_PATH);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=\"struts2_tutorial.pdf\"");

		return response.build();

	}

	@POST
	@Path("/dilan9/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String getFileUpload(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "C://uploaded/"
				+ fileDetail.getFileName();

		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return output;

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	
	@GET
	@Path("/dilan10/xml/book/{isbn}")
	@Produces(MediaType.APPLICATION_XML)
	public Book getXml(@PathParam("isbn") int isbn) {

		Book myBook = new Book();
		myBook.setAuthor("Dilan");
		myBook.setName("Whitecollar");
		myBook.setISBN(isbn);
		
		return myBook;

	}
	
	@GET
	@Path("/dilan10/json/book/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getJsonBook(@PathParam("isbn") int isbn) {

		Book myBook = new Book();
		myBook.setAuthor("Dilan");
		myBook.setName("Whitecollar");
		myBook.setISBN(isbn);
		
		return myBook;

	}
	
	
	@GET
	@Path("/dilan10/json/")
	@Produces(MediaType.APPLICATION_JSON)
	public Song getJson() {

		Song mySong = new Song();
		mySong.setSinger("Dilan");
		mySong.setTitle("REST");
		
		return mySong;

	}
	

}
