import org.foo.MyPipeline

def call(String name) {
    MyPipeline pipeline = new MyPipeline(this)

    pipeline.execute(name)
}