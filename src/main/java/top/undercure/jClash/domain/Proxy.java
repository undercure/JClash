package top.undercure.jClash.domain;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author underCure
 * @date 2023/02/13 22:33
 */
@Data
public class Proxy {
    private ArrayList<String> all;
    private ArrayList<String> history;
    private String name;
    private String now;
    private boolean udp;
}
