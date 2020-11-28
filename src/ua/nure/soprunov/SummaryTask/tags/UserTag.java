package ua.nure.soprunov.SummaryTask.tags;

import ua.nure.soprunov.SummaryTask.dao.entity.User;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class UserTag extends SimpleTagSupport {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        System.out.println("user Avatar " + user.getUserAvatar());
            out.write("<img  src=\"images/" +user.getUserAvatar() +"\" alt=\"Avatar\">");
        }
}
