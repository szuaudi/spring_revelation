package me.aodi.spring_revelation.ioc.service;

public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newsPersister;

    public FXNewsProvider() {
    }

    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersister newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewsPersister() {
        return newsPersister;
    }

    public void setNewsPersister(IFXNewsPersister newsPersister) {
        this.newsPersister = newsPersister;
    }

    public void getAndPersistNews() {
        System.out.println("get news from " + newsListener + " and use " + newsPersister + " to persist news");
    }
}
