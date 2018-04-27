package com.osaf.root.moviepedia.model;

public class Movies {

    //private String Results ;
    private String PosterPath;

    public Movies(){

    }
    public Movies(String posterPath){
        this.PosterPath = posterPath;
      //  this.Results = results;
    }

    public String getPosterPath() { return PosterPath; }
    //public String getResults() { return Results; }


    public void setPosterpath(String posterPath){this.PosterPath = posterPath; }
    //public void setResults(String results){this.Results = results; }

}
