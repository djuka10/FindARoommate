package rs.ac.uns.ftn.findaroommate.dto;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto   {

	@Expose
	private String subject;
	@Expose
	private String content;
	@Expose
	private String additionalInfo;
	@Expose
	private String sendTo;

}
