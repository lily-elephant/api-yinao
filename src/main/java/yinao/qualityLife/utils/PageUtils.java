package yinao.qualityLife.utils;

import java.util.List;

public class PageUtils {
	public static <T> List getList(List<T> list, int currPage , int pageSize) {
		List<T> ben_list ; 
		//从第几条数据开始
		int firstIndex = (currPage - 1) * pageSize;
		//到第几条数据结束
		int lastIndex = currPage * pageSize;
		int length = list.size() ; 	
		if(length % pageSize != 0) {
			if(currPage > (length / pageSize) + 1  ) {
				return null ;
			}else if(currPage == (length / pageSize) + 1){
				ben_list = list.subList(firstIndex, length) ; 			
			}else{
				ben_list = list.subList(firstIndex, lastIndex) ; 
			}
		}else{
			if(currPage > (length / pageSize)) {
				return null ;
			}else{
				ben_list = list.subList(firstIndex, lastIndex) ; 
			}
		}
		return ben_list ; 
    }
}