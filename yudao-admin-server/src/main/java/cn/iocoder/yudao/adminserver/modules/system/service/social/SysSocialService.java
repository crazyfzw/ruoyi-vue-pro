package cn.iocoder.yudao.adminserver.modules.system.service.social;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.adminserver.modules.system.dal.dataobject.social.SysSocialUserDO;
import cn.iocoder.yudao.adminserver.modules.system.enums.social.SysSocialTypeEnum;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import me.zhyd.oauth.model.AuthUser;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 社交 Service 接口，例如说社交平台的授权登录
 *
 * @author 芋道源码
 */
public interface SysSocialService {

    /**
     * 获得社交平台的授权 URL
     *
     * @param type 社交平台的类型 {@link SysSocialTypeEnum}
     * @param redirectUri 重定向 URL
     * @return 社交平台的授权 URL
     */
    String getAuthorizeUrl(Integer type, String redirectUri);

    /**
     * 获得授权的用户
     * 如果授权失败，则会抛出 {@link ServiceException} 异常
     *
     * @param type 社交平台的类型 {@link SysSocialTypeEnum}
     * @param code 授权码
     * @param state state
     * @return 授权用户
     */
    @NotNull
    AuthUser getAuthUser(Integer type, String code, String state);

    default String getAuthUserUnionId(AuthUser authUser) {
        return StrUtil.blankToDefault(authUser.getToken().getUnionId(), authUser.getUuid());
    }

    /**
     * 获得 unionId 对应的某个社交平台的“所有”社交用户
     * 注意，这里的“所有”，指的是类似【微信】平台，包括了小程序、公众号、PC 网站，他们的 unionId 是一致的
     *
     * @param type 社交平台的类型 {@link SysSocialTypeEnum}
     * @param unionId 社交平台的 unionId
     * @return 社交用户列表
     */
    List<SysSocialUserDO> getAllSocialUserList(Integer type, String unionId);

    /**
     * 获得指定用户的社交用户列表
     *
     * @param userId 用户编号
     * @return 社交用户列表
     */
    List<SysSocialUserDO> getSocialUserList(Long userId);

    /**
     * 绑定社交用户
     *
     * @param userId 用户编号
     * @param type 社交平台的类型 {@link SysSocialTypeEnum}
     * @param authUser 授权用户
     */
    void bindSocialUser(Long userId, Integer type, AuthUser authUser);

    /**
     * 取消绑定社交用户
     *
     * @param userId 用户编号
     * @param type 社交平台的类型 {@link SysSocialTypeEnum}
     * @param unionId 社交平台的 unionId
     */
    void unbindSocialUser(Long userId, Integer type, String unionId);

}
