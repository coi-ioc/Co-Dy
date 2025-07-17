package Controller;

import View.AlertView;
import View.CommentG;
import View.CommentsTabView;
import View.HomePageView;
import database.CommentDAO;
import model.User;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * La classe CommentsTabController rappresenta il controller per la tab dei commenti
 * Questa classe fornisce metodi per gestire la tab dei commenti
 *
 * @author Corrado
 * @version 1.0
 */
public class CommentsTabController {

    /**
     * Costruttore per la classe CommentsTabController
     * imposta i listener per la tab dei commenti
     * @param view Vista della tab dei commenti
     * @param user Utente che ha aperto la tab
     * @param post Post a cui saranno associati i commenti
     */
    public CommentsTabController(CommentsTabView view, User user, model.Post post) {



        view.getCommentBtn().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getCommentIn().isEmpty()) {
                    new AlertView("Non puoi pubblicare un commento vuoto", view.getFrame());
                    return;
                }
                if (view.getCommentIn().getText().startsWith(" ")) {
                    new AlertView("il commento non puo' iniziare con uno spazio", view.getFrame());
                    view.getCommentIn().setText("");
                    return;
                }
                if (view.getCommentIn().getText().equals("Scrivi un commento")) {

                    return;
                }
                model.Comment c = new model.Comment(view.getCommentIn().getText(), user);
                if (new CommentDAO(c, post, user).commented()) {
                    view.getCommentIn().setText("");
                    view.getPanel().add(new CommentG(c));
                    view.getPanel().add(Box.createVerticalStrut(10));
                    view.getPanel().revalidate();
                }
            }


        });

        ArrayList<model.Comment> comments = new CommentDAO().readPostComments(post);
        for (model.Comment c : comments) {
            view.getPanel().add(new CommentG(c));
            view.getPanel().add(Box.createVerticalStrut(10));
        }

        view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new HomePageController(user, new HomePageView(user));
            }
        });

    }

}







