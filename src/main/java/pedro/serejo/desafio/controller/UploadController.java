package pedro.serejo.desafio.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("upload")
public class UploadController {

	@GetMapping("form")
	public String getUploadForm() {
		return "UploadForm";
	}

	@PostMapping("uploadFile")
	public Object uploadCsvFile(MultipartFile file) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String [] csvLines = br.lines().toArray(x->new String[x]);

		System.out.println(csvLines[2]);
		
		return new RedirectView("/upload/form");
	}

}
