package ru.spbau.jvm.scala.tree

case class Node[K, P](
  private var key: K,
  private var pri: P,
  private var left: Tree[K, P] = Leaf(),
  private var right: Tree[K, P] = Leaf()
)(implicit ordK: K => Ordered[K], ordP: P => Ordered[P])
    extends Tree[K, P] {

  override def split(key: K): (Tree[K, P], Tree[K, P]) = {
    if (this.key < key) {
      val (t1, t2) = right.split(key)
      (Node(key, pri, left, t1), t2)
    } else {
      val (t1, t2) = left.split(key)
      (t1, Node(key, pri, t2, right))
    }
  }

  override def merge(tree: Tree[K, P]): Tree[K, P] = {
    tree match {
      case Leaf() => this
      case Node(thatKey, thatPri, thatLeft, thatRight) =>
        if (pri < thatPri) {
          Node(thatKey, thatPri, merge(thatLeft), thatRight)
        } else {
          Node(thatKey, thatPri, thatLeft, right.merge(tree))
        }
    }
  }

  override def isEmpty(): Boolean = false

  override def insert(k: K, p: P): Tree[K, P] = {
    val (t1, t2) = split(k)
    t1.merge(Node(k, p)).merge(t2)
  }

  override def remove(k: K): (Option[P], Tree[K, P]) = {
    if (key == k) {
      return (Some(pri), left.merge(right))
    }

    if (key < k) {
      val (optionPri, newRight) = right.remove(k)
      (optionPri, Node(key, pri, left, newRight))
    } else {
      val (optionPri, newLeft) = left.remove(k)
      (optionPri, Node(key, pri, newLeft, right))
    }
  }

  override def toList: List[(K, P)] =
    left.toList ++ List((key, pri)) ++ right.toList
}
