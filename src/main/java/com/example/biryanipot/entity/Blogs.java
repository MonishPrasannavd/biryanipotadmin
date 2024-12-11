package com.example.biryanipot.entity;

import jakarta.persistence.*;

import com.example.biryanipot.entity.Category;

import java.util.Objects;

@Entity
@Table(name = "blogs")
public class Blogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")  // Fix this line
    private Category category;

    @Column(name = "blog_title", nullable = false)
    private String blogTitle;

    @Column(name = "blog_overview", nullable = false)
    private String blogOverview;

    @Column(name = "image_alt_tag", nullable = false)
    private String imageAltTag;

    @Column(name = "meta_title", nullable = false)
    private String metaTitle;

    @Column(name = "meta_keywords", nullable = false)
    private String metaKeywords;

    @Column(name = "blog_link", nullable = false)
    private String blogLink;

    @Column(name = "meta_description", nullable = false)
    private String metaDescription;

    @Column(name = "blog_description", nullable = false)
    private String blogDescription;

    @Column(name = "image_path", nullable = false)
    private String imagePath;  // Store the path to the image

    // Getters and Setters

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogOverview() {
        return blogOverview;
    }

    public void setBlogOverview(String blogOverview) {
        this.blogOverview = blogOverview;
    }

    public String getImageAltTag() {
        return imageAltTag;
    }

    public void setImageAltTag(String imageAltTag) {
        this.imageAltTag = imageAltTag;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Override equals and hashCode based on blogId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blogs blogs = (Blogs) o;
        return Objects.equals(blogId, blogs.blogId);
    }

    @Override
    public String toString() {
        return "Blogs [blogId=" + blogId + ", category=" + category + ", blogTitle=" + blogTitle + ", blogOverview="
                + blogOverview + ", imageAltTag=" + imageAltTag + ", metaTitle=" + metaTitle + ", metaKeywords="
                + metaKeywords + ", blogLink=" + blogLink + ", metaDescription=" + metaDescription
                + ", blogDescription=" + blogDescription + ", imagePath=" + imagePath + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId);
    }
}
