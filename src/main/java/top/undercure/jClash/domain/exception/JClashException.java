package top.undercure.jClash.domain.exception;

/**
 * @author underCure
 * @date 2023/02/14 13:04
 */

public class JClashException extends RuntimeException {
    public JClashException(){
        super();
    }
    public JClashException(String s){
        super(s);
    }
}
