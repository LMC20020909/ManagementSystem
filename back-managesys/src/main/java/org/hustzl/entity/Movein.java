package org.hustzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Movein implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String employeeId;

    private String jobId;

    private String createDate;


}
