package model;




public class News {
   private int post_id;
    private String title;
    private String content;
    private int created_by;
    private int category_id;
    private int status;
    private String image;
    private String detail;
    private String created_at;
    private String updated_at;
    
    public News() {
    }

    public News(int post_id, String title, String content, int created_by, int category_id, int status, String image, String detail, String created_at, String updated_at) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.created_by = created_by;
        this.category_id = category_id;
        this.status = status;
        this.image = image;
        this.detail = detail;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "News{" + "post_id=" + post_id + ", title=" + title + ", content=" + content + ", created_by=" + created_by + ", category_id=" + category_id + ", status=" + status + ", image=" + image + ", detail=" + detail + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }

   
}