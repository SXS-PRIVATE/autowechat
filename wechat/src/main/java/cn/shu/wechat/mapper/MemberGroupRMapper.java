package cn.shu.wechat.mapper;

import cn.shu.wechat.beans.pojo.MemberGroupR;
import cn.shu.wechat.beans.pojo.MemberGroupRExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @作者 舒新胜
 * @项目 AutoWechat
 * @创建时间 6/14/2021 7:47 PM
 */
public interface MemberGroupRMapper {
    long countByExample(MemberGroupRExample example);

    int deleteByExample(MemberGroupRExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberGroupR record);

    int insertOrUpdate(MemberGroupR record);

    int insertOrUpdateSelective(MemberGroupR record);

    int insertSelective(MemberGroupR record);

    List<MemberGroupR> selectByExample(MemberGroupRExample example);

    MemberGroupR selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberGroupR record, @Param("example") MemberGroupRExample example);

    int updateByExample(@Param("record") MemberGroupR record, @Param("example") MemberGroupRExample example);

    int updateByPrimaryKeySelective(MemberGroupR record);

    int updateByPrimaryKey(MemberGroupR record);

    int updateBatch(List<MemberGroupR> list);

    int batchInsert(@Param("list") List<MemberGroupR> list);
}