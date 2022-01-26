output "output_webserver_ids_tf"{
    value = aws_instance.java10x_cyberg3_app_tf.*.id
}

