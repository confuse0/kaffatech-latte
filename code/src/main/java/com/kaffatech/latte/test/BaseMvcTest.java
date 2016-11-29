package com.kaffatech.latte.test;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.bean.util.BeanUtils;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.beans.PropertyDescriptor;

/**
 * @author lingzhen on 16/8/29.
 */
public abstract class BaseMvcTest extends AbstractJUnit4SpringContextTests {

    private MockMvc mockMvc;

    @Before
    public void baseBefore() {
        System.setProperty("unitTest", "true");
        // 初始化
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(getControllers()).build();
    }

    protected <T> T postJson(String url, BaseBean req, Class<T> resClass) throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post(url).characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJsonString(req)).accept(
                MediaType.APPLICATION_JSON);
        MvcResult res = mockMvc.perform(reqBuilder).andReturn();
        return JsonUtils.toJsonObject(res.getResponse().getContentAsString(), resClass);
    }

    protected <T> T post(String url, BaseBean req, Class<T> resClass) throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post(url).characterEncoding("UTF-8").contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(
                MediaType.APPLICATION_JSON);

        // 设置表单参数
        PropertyDescriptor[] fields = BeanUtils.getPropertyDescriptors(req.getClass());

        for (PropertyDescriptor each : fields) {
            String key = each.getName();
            String value = BeanUtils.getPropertyString(req, key);
            if (!StringUtils.equals("class", key)) {
                reqBuilder.param(key, value);
            }
        }

        MvcResult res = mockMvc.perform(reqBuilder).andReturn();
        return JsonUtils.toJsonObject(res.getResponse().getContentAsString(), resClass);
    }

    protected abstract Object[] getControllers();
}
