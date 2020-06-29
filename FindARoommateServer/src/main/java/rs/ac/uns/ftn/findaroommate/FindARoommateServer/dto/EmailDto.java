package rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto   {
	
	private String subject;
	private String content;
	private String messageType;
	private String sendTo;
	private String sendFrom;
	
	private boolean multipart;
	private String encoding;
	private String additionalInfo;

}
