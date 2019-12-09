package ru.spbau.jvm.scala.tree

case class Leaf[K, P]() extends Tree[K, P] {
  override def split(key: K): (Tree[K, P], Tree[K, P]) = (Leaf(), Leaf())

  override def merge(tree: Tree[K, P]): Tree[K, P] = tree

  override def isEmpty(): Boolean = true

  override def insert(k: K, p: P): Tree[K, P] = Node(k, p)

  override def remove(k: K): (Option[P], Tree[K, P]) = (None, this)

  override def toList: List[(K, P)] = List()
}
