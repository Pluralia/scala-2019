package ru.spbau.jvm.scala.tree

trait Tree[K, P] {
  def split(key: K): (Tree[K, P], Tree[K, P])

  def merge(tree: Tree[K, P]): Tree[K, P]

  def isEmpty(): Boolean

  def insert(k: K, p: P): Tree[K, P]

  def remove(k: K): (Option[P], Tree[K, P])

  def toList: List[(K, P)]

  def iterator(): Iterator[(K, P)] = toList.iterator

  def keyIterator(): Iterator[K] = {
    toList.iterator.map(x => x._1)
  }
}
