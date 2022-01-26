variable "var_region_name_tf" {
  default = "eu-west-1"
}

variable "var_global_key_name_tf" {
    default = "cyber-10x-group3"
}

variable "var_private_key_loc_tf" {
  default = "/home/vagrant/.ssh/cyber-10x-group3.pem"
}

locals {
  vpc_id = "${aws_vpc.java10x_cyberg3_vpc_tf.id}"
}







