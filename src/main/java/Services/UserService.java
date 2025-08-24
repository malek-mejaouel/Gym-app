package Services;

import Entities.User;
import javafx.scene.control.Alert;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class UserService implements IUser<User> {

    public static int idUser;
    static Connection connection;
    public UserService(){
        connection= Database.getInstance().getConnection();

    }

    public static List<User> afficher() throws SQLException {
        List<User> clients= new ArrayList<>();
        String req="select * from user";
        Statement st  = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            User p = new User();
            p.setId(rs.getInt(1));
            p.setNom(rs.getString(2));
            p.setPrenom(rs.getString(3));
            p.setEmail(rs.getString(4));
            p.setMdp(rs.getString(5));
            p.setTel(rs.getString(6));
            clients.add(p);
        }
        return clients;
    }



    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO user (id,username,password,email,phone,role) VALUES(?,?,?,?,?)" ;
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, user.getUsername());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPassword());
        stmt.setString(5, user.getPhone());
        stmt.setString(4, user.getRole());

        int result=stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }

    @Override
    public  void modifier(String Nom, String Prenom, String Email, String Mdp, String Tel,int id) throws SQLException {
        String req = "UPDATE user SET Nom=?, Prenom=?, Email=?, Mdp=?, Tel=? WHERE id=?";

        PreparedStatement ps=connection.prepareStatement(req);

        ps.setString(1, Nom);
        ps.setString(2,Prenom);


        ps.setString(3, Email);
        ps.setString(4,Mdp);
        ps.setString(5,Tel);
        ps.setInt(6,id);





        ps.executeUpdate();
        System.out.println("User Updated Successfully!");
    }


    /*public static void modifier(User user)  {
        String req = "UPDATE user SET Nom=?, Prenom=?, Email=?, Mdp=?, Tel=? WHERE id=?";
        try {
            PreparedStatement ps=connection.prepareStatement(req);

            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());


            ps.setString(3, user.getEmail());
            ps.setString(4,user.getMdp());
            ps.setString(5,user.getTel());
            ps.setInt(6,user.getId());




            ps.executeUpdate();
            System.out.println("User Updated Successfully!");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public boolean existemail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE Email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;

        }
        return exist;
    }

    public void supprimer(User client) throws SQLException {
        String req = "DELETE FROM user WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(req)) {
            stmt.setInt(1, client.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> Search(String t) throws SQLException {
        List<User> list1 = new ArrayList<>();
        List<User> list2 = afficher();
        list1 = (list2.stream().filter(c -> c.getNom().startsWith(t)).collect(Collectors.toList()));

        return list1;
    }
    /*@Override
    public void modifier(String nom ,String prenom, String email,String mdp,String tel) throws SQLException {
        String sql = "UPDATE livreur SET nom = ?, prenom = ?, region = ? WHERE nom = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setString(3, region);

        preparedStatement.executeUpdate();
    }*/


    public void supprimer(String nom) throws SQLException {
        String sql = "delete from user where Nom = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nom);
        preparedStatement.executeUpdate();
    }
    public int getNombreDeUser(Connection conn, String role) throws SQLException {
        String query = "SELECT COUNT(*) AS nombre_de_user FROM user WHERE Role = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, role);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("nombre_de_user"); // Correction du nom du champ
                }
            }
        }
        return 0; // Retourner une valeur par défaut si aucun résultat n'est trouvé
    }
    public void updateUserPasswordByEmail(String email, String newPassword) {
        try {
            String requeteUpdate = "UPDATE user SET Mdp=? WHERE Email=?";

            PreparedStatement ps = connection.prepareStatement(requeteUpdate);

            ps.setString(1, newPassword);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("Mot de passe modifié avec succès pour l'utilisateur avec l'email : " + email);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

/*







    public List<User> triEmail() {

        List<User> list1 = new ArrayList<>();
        List<User> list2 = null;
        try {
            list2 = afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        list1 = list2.stream().sorted((o1, o2) -> o1.getEmail().compareTo(o2.getEmail())).collect(Collectors.toList());
        return list1;

    }





    public User getUserById(int userId) throws SQLException {
        User user = null;
        String req = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("FullName"));
            user.setEmail(rs.getString("EMAIL"));
            user.setMdp(rs.getString("MDP"));
            user.setTel(rs.getString("TEL"));
            user.setImage(rs.getString("IMAGE"));
        }
        return user;
    }


    public User findById(int id) throws SQLException {
        User u = new User();
        try {
            String sql = "SELECT * FROM user WHERE id = " + id;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("EMAIL"));
                u.setMdp(rs.getString("MDP"));
                u.setTel(rs.getString("TEL"));
                u.setImage(rs.getString("IMAGE"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }*/

}
