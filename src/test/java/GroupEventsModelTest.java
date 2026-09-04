import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequest;
import io.github.kloping.qqbot.impl.registers.GroupEventsRegister;
import org.junit.Test;

import static org.junit.Assert.*;

/** 群新事件模型与注册分发回归测试。 */
public class GroupEventsModelTest {
    private final GroupEventsRegister register = new GroupEventsRegister();

    @Test
    public void dispatchesNewGroupEvents() {
        RawMessage message = new RawMessage();
        JSONObject data = JSON.parseObject("{\"group_openid\":\"g1\",\"timestamp\":123,\"op_member_openid\":\"m1\"}");
        assertEquals("BaseGroupMsgReceiveEvent", register.handle(GroupEventsRegister.GROUP_MSG_RECEIVE, data, message).getClass().getSimpleName());
        assertEquals("BaseGroupMsgRejectEvent", register.handle(GroupEventsRegister.GROUP_MSG_REJECT, data, message).getClass().getSimpleName());
        assertEquals("BaseGroupMemberAddEvent", register.handle(GroupEventsRegister.GROUP_MEMBER_ADD, data, message).getClass().getSimpleName());
        assertEquals("BaseGroupMemberRemoveEvent", register.handle(GroupEventsRegister.GROUP_MEMBER_REMOVE, data, message).getClass().getSimpleName());
    }

    @Test
    public void mapsJoinRequestExtensions() {
        JSONObject data = JSON.parseObject("{\"group_openid\":\"g1\",\"join_request_id\":\"r1\",\"member_openid\":\"m1\",\"auto_approved\":{\"strategy_id\":\"s1\"}}");
        JoinRequest request = data.toJavaObject(JoinRequest.class);
        assertEquals("r1", request.getJoinRequestId());
        assertNotNull(request.getAutoApproved());
        assertEquals("s1", request.getAutoApproved().getStrategyId());
    }
}
