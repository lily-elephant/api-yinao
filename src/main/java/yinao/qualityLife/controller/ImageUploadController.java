package yinao.qualityLife.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import yinao.qualityLife.dao.sourceMapper;
import yinao.qualityLife.model.ResultMap;
import yinao.qualityLife.model.domain.SourcePicture;
import yinao.qualityLife.model.domain.SourceVideo;
import yinao.qualityLife.model.domain.UploadPictures;
import yinao.qualityLife.model.domain.UploadVideos;
import yinao.qualityLife.utils.TokenUtils;

@RestController
@CrossOrigin
public class ImageUploadController {
		private final sourceMapper sourceMapper;
		private final TokenUtils tokenUtils;
		@Value("${VIDEO_FOLDER}")
		private String VIDEO_FOLDER;
		@Value("${PICTURE_FOLDER}")
		private String PICTURE_FOLDER;
		@Value("${FILE_FOLDER}")
		private String FILE_FOLDER;
		
		public ImageUploadController(sourceMapper sourceMapper,TokenUtils tokenUtils) {
			this.sourceMapper = sourceMapper;
			this.tokenUtils= tokenUtils;
		}
		
		@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	    public ResultMap singleFileUpload(MultipartFile file,HttpServletRequest request) {
	        if (file.isEmpty()) {
	        	return new ResultMap().fail("1").message("文件为空，请重新上传参数").data("");
	        }
	        String type=request.getParameter("type");
	        try {
        		byte[] bytes = file.getBytes();
	        	Long time = System.currentTimeMillis() ;
				Random ran1 = new Random(100000) ;
				int number = ran1.nextInt(10) ;
				//3.获得资源文件后缀并重新命名
				String lds = file.getOriginalFilename(); 
				String benben = lds.substring(lds.lastIndexOf(".")) ; 
				if(type.equals("video")) {
					String path_params ="/v_"+ time + number + benben ;
					Path path = Paths.get(VIDEO_FOLDER + path_params);
					//如果没有files文件夹，则创建
		            if (!Files.isWritable(path)) {
		                Files.createDirectories(Paths.get(VIDEO_FOLDER));
		            }
		            UploadVideos videos=new UploadVideos();
		            //文件写入指定路径
		            Files.write(path, bytes);
		            List<SourceVideo> sourceVideos=this.sourceMapper.getVideoSorts();
		            int videoSort=Integer.parseInt(sourceVideos.get(0).getSort())+1;
		            videos.setSort(videoSort);
		            File source= new File(VIDEO_FOLDER + path_params);
		            Encoder encoder = new Encoder();
		            try {
		            	MultimediaInfo m = encoder.getInfo(source);
		            	long ls = m.getDuration();
		            	System.out.println(ls);
		            	//System.out.println("此视频时长为:"+ls/60000+"分"+(ls`000)/1000+"秒！");
		            	String minute=String.valueOf(ls/1000);
		            	  videos.setDuration(minute);
		            } catch(Exception e) {
		            	e.printStackTrace();
		            }
		            videos.setName(lds);
		            videos.setSize(file.getSize());
		            videos.setPath("/videos"+path_params);
		            return new ResultMap().success().message("文件写入成功...").data(videos);	
				}else if(type.equals("file")) {
					String path_params ="/f_"+ time + number + benben ;
					Path path = Paths.get(FILE_FOLDER + path_params);
					//如果没有files文件夹，则创建
		            if (!Files.isWritable(path)) {
		                Files.createDirectories(Paths.get(FILE_FOLDER));
		            }
		            UploadVideos files=new UploadVideos();
		            //文件写入指定路径
		            Files.write(path, bytes);
		           
		            files.setName(lds);
		            files.setSize(file.getSize());
		            files.setPath("/files"+path_params);
		            return new ResultMap().success().message("文件写入成功...").data(files);	
				}
				else {
					String path_params ="/p_"+ time + number + benben ;
					Path path = Paths.get(PICTURE_FOLDER + path_params);
					BufferedImage image = ImageIO.read(file.getInputStream());
					//如果没有files文件夹，则创建
		            if (!Files.isWritable(path)) {
		                Files.createDirectories(Paths.get(PICTURE_FOLDER));
		            }
		            //文件写入指定路径
		            Files.write(path, bytes);
		            List<SourcePicture> banners=this.sourceMapper.getSorts();
		           UploadPictures photo=new UploadPictures(file.getSize() , image.getHeight() , image.getWidth() , Integer.parseInt(banners.get(0).getSort())+1 , "/images"+path_params , lds);
		           return new ResultMap().success().message("文件上传成功...").data(photo);
				}
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResultMap().fail("1").message("后端异常...").data("");
	        }
	    }
		
		
		
		
		
		
}
