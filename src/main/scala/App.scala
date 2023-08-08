import zio.*
import zio.direct.*
import zio.http.*
import zio.stream.*


object App extends ZIOAppDefault:

  given CanEqual[Path, Path] = CanEqual.derived
  given CanEqual[Method, Method] = CanEqual.derived

  private def app(words: Chunk[String]) = Http.collectZIO:
    case Method.GET -> Path.root =>
      defer:
        val random = Random.nextIntBounded(words.size).run
        Response.text(words(random))

  def run =
    val inputStream = this.getClass.getClassLoader.nn.getResourceAsStream("words.txt").nn
    val stream = ZStream.fromInputStream(inputStream).via(ZPipeline.utf8Decode >>> ZPipeline.splitLines)

    // todo: defer but needs this fixed https://github.com/zio/zio-direct/issues/53
    for
      words <- stream.runCollect
      _ <- Server.serve(app(words).withDefaultErrorResponse).provide(Server.default)
    yield
      ()
