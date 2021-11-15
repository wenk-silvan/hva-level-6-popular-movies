package nl.hva.madlevel6task2

object Constants {
    // API Options
    const val apiKey = "?api_key=a3f832dfdb8c667bb58708863b682739"
    const val apiTimeout: Long = 5_000

    // API for Movies
    const val baseUrlMovie = "https://api.themoviedb.org/3/discover/movie/"
    const val year = "primary_release_year"

    // API for Images
    const val baseUrlImg = "https://image.tmdb.org/t/p/"
    const val imgSizeXS = "w100/"
    const val imgSizeS = "w200/"
    const val imgSizeM = "w300/"
    const val imgSizeL = "w400/"
    const val imgSizeXL = "w500/"
}