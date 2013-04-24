package pl.itcrowd.base.web;

import javax.servlet.http.HttpServlet;

public class PhotoServlet extends HttpServlet {

//    private static final String DEFAULT_PHOTO_TYPE = "png";
//
//    private byte[] avatar_placeholder;
//
//    private byte[] thumbnail_placeholder;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @SuppressWarnings("CdiInjectionPointsInspection")
//    @Inject
//    private Logger logger;
//
//    @Override
//    public void init()
//    {
//        try {
//            BufferedImage bufferedImage = ImageIO.read(getServletContext().getResource("/img/avatar_placeholder.png"));
//            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//            ImageIO.write(bufferedImage, DEFAULT_PHOTO_TYPE, baos1);
//            baos1.flush();
//            avatar_placeholder = baos1.toByteArray();
//
//            bufferedImage = ImageIO.read(getServletContext().getResource("/img/placeholder.png"));
//            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//            ImageIO.write(bufferedImage, DEFAULT_PHOTO_TYPE, baos2);
//            baos2.flush();
//            thumbnail_placeholder = baos2.toByteArray();
//        } catch (IOException e) {
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    {
//        final Long id = Long.parseLong(request.getParameter("id"));
//
//        if (request.getRequestURI().contains("photo")) {
//            File photo = entityManager.find(File.class, id);
//            if (photo != null) {
//                response.setHeader("Content-Type", getServletContext().getMimeType(DEFAULT_PHOTO_TYPE));
//                response.setHeader("Content-Disposition", "inline; filename=\"" + "photo.png" + "\"");
//                writeToResponse(response, photo.getContent());
//            }
//        } else if (request.getRequestURI().contains("thumbnail")) {
//
//            Product product = entityManager.find(Product.class, id);
//            if (product != null) {
//                byte[] thumbnail = product.getThumbnail();
//                if (thumbnail == null) {
//                    writeToResponse(response, thumbnail_placeholder);
//                } else {
//                    response.setHeader("Content-Type", getServletContext().getMimeType(DEFAULT_PHOTO_TYPE));
//                    writeToResponse(response, thumbnail);
//                }
//            }
//        } else if (request.getRequestURI().contains("avatar")) {
//
//            Psychic psychic = entityManager.find(Psychic.class, id);
//            if (psychic != null) {
//                byte[] avatar = psychic.getPsychicAvatar();
//                if (avatar == null) {
//                    writeToResponse(response, avatar_placeholder);
//                } else {
//                    response.setHeader("Content-Type", getServletContext().getMimeType(DEFAULT_PHOTO_TYPE));
//                    writeToResponse(response, avatar);
//                }
//            }
//        }
//    }
//
//    private void writeToResponse(HttpServletResponse response, byte[] image)
//    {
//        try {
//            response.getOutputStream().write(image);
//            response.getOutputStream().close();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }
}
