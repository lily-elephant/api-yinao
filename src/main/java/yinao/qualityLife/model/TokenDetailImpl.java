package yinao.qualityLife.model;

public class TokenDetailImpl implements TokenDetail {

    private final String username; 
    private final String usertype ; 
    

    public TokenDetailImpl(String username , String usertype) {
        this.username = username ;
        this.usertype = usertype ; 
        
    }

    @Override
    public String getUsername() {
        return this.username ;
    }

	@Override
	public String getUsertype() {
		// TODO Auto-generated method stub
		return this.usertype ;
	}
    
    
}
