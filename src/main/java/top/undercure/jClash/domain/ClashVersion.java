package top.undercure.jClash.domain;

import lombok.Data;

/**
 * @author underCure
 * @date 2023/02/14 12:21
 */
@Data
public class ClashVersion {
    private boolean premium;

    private String version;
}