package by.epam.javawebtraining.kukareko.horseracebet.controller.tag;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author Yulya Kukareko
 * @version 1.0 05 May 2019
 */
public class NotFoundTag extends TagSupport {

    private static ConfigurationManager configurationManager;

    public NotFoundTag() {
        configurationManager = ConfigurationManager.getInstance();
    }

    @Override
    public int doStartTag() throws JspException {
        String errorInfo = configurationManager.getProperty("errorMessage404");

        try {
            JspWriter out = pageContext.getOut();
            out.write(errorInfo);
        } catch (IOException ex) {
            throw new JspException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
