import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.AtAll;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.utils.PdCode;
import org.junit.Test;

import static org.junit.Assert.*;

public class PdCodeTest {

    @Test
    public void deserializesMixedPdCode() {
        MessageChain chain = PdCode.deserializePdCode(
                "hello<at:123><emoji:14><image:https://example.com/a.png><#456>world");

        assertEquals(6, chain.size());
        assertEquals("hello", ((PlainText) chain.get(0)).getText());
        assertEquals("123", ((At) chain.get(1)).getTargetId());
        assertEquals(Integer.valueOf(14), ((Emoji) chain.get(2)).getId());
        assertEquals("https://example.com/a.png", ((Image) chain.get(3)).getUrl());
        assertEquals(At.CHANNEL_TYPE, ((At) chain.get(4)).getType());
        assertEquals("world", ((PlainText) chain.get(5)).getText());
    }

    @Test
    public void deserializesQqFormatsAndQuotedFaceId() {
        MessageChain chain = PdCode.deserializePdCode(
                "<qqbot-at-user id=\"u1\"/><faceType=1,faceId=\"14\",faceText=微笑>"
                        + "<atAll/>@everyone<qqbot-at-everyone />");

        assertEquals(5, chain.size());
        assertEquals("u1", ((At) chain.get(0)).getTargetId());
        assertEquals(Integer.valueOf(14), ((Emoji) chain.get(1)).getId());
        assertTrue(chain.get(2) instanceof AtAll);
        assertTrue(chain.get(3) instanceof AtAll);
        assertTrue(chain.get(4) instanceof AtAll);
    }

    @Test
    public void deserializesAlphanumericAtId() {
        MessageChain chain = PdCode.deserializePdCode("<@AC80D2CB54FEDCEE49F832D6D3214EA2>");

        assertEquals(1, chain.size());
        assertEquals("AC80D2CB54FEDCEE49F832D6D3214EA2", ((At) chain.get(0)).getTargetId());
    }

    @Test
    public void keepsMalformedEmojiAsText() {
        MessageChain chain = PdCode.deserializePdCode("a<emoji:999999999999999999999>b");

        assertEquals(3, chain.size());
        assertEquals("a", ((PlainText) chain.get(0)).getText());
        assertEquals("<emoji:999999999999999999999>", ((PlainText) chain.get(1)).getText());
        assertEquals("b", ((PlainText) chain.get(2)).getText());
    }
}
