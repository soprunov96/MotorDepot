package ua.nure.soprunov.SummaryTask4.web.command.common;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.Util.Fields;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.entity.User;
import ua.nure.soprunov.SummaryTask4.dao.implementation.UserDaoImpl;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.exception.Messages;
import ua.nure.soprunov.SummaryTask4.web.command.Command;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


@MultipartConfig
public class ChangeAvatarCommand extends Command {

    private String fileName;
    private String command;
    private String userId;
    private final int fileMaxSize = 100 * 1024;
    private final int memMaxSize = 100 * 1024;
    private String UPLOAD_DIRECTORY = "E:/Project/Motor-Depot-Motor-depot/Motor-Depot-Motor-depot/WebContent/images";



    private static final Logger LOG = Logger.getLogger(ChangeAvatarCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Command execution starts");





        System.out.println(request.getContextPath());


        ServletFileUpload upload = null;
        if (ServletFileUpload.isMultipartContent(request)) {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(fileMaxSize);
            upload.setSizeMax(memMaxSize);
            String uploadPath = request.getServletContext().getRealPath("")
                    + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

        }
        if (ServletFileUpload.isMultipartContent(request)) {

            List<FileItem> formItems = null;
            try {
                assert upload != null;
                formItems = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
                        File storeFile = new File(filePath);
                        try {
                            item.write(storeFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LOG.debug("File has been uploaded! ");
                    }
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String value = item.getString();
                        if (Fields.USER_ID.equals(fieldName)) {
                            userId = value;

                            LOG.trace("Get attribute 'userId': " + userId);
                        }
                        if (Fields.PREVIOUS_COMMAND.equals(fieldName)) {
                            command = value;
                            LOG.trace("Get attribute 'previousCommand': " + command);
                        }

                    }
                }
            }
        }


        LOG.trace("Get attribute 'fileName': " + fileName);

        User user = new UserDaoImpl(DataSourceFactory
                .getDataSource(DataSourceType.MY_SQL_DATASOURCE)).find(Long.parseLong(userId));
        LOG.trace("Find user : " + user);

        user.setUserAvatar(fileName);

        new UserDaoImpl(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE)).update(user);


        try {
            if (command == null) {

                LOG.trace("Return " + Path.COMMAND_SHOW_START_PAGE);
                LOG.debug("Finished executing Command");
                return Path.COMMAND_SHOW_START_PAGE;
            } else if (command.equals("")) {
                LOG.trace("Return " + Path.COMMAND_SHOW_START_PAGE);
                LOG.debug("Finished executing Command");
                return Path.COMMAND_SHOW_START_PAGE;
            }
        } catch
        (Exception ex) {
            LOG.error(Messages.NULL_POINTER_EXEPTION, ex);
            LOG.trace("Return " + Path.COMMAND_SHOW_START_PAGE);
            LOG.debug("Finished executing Command");
            return Path.COMMAND_SHOW_START_PAGE;
        }
        LOG.debug("Finished executing Command");

        return "?" + command;

    }
}
