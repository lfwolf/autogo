# 配置基础镜像
# FROM alpine:latest
FROM frolvlad/alpine-glibc:alpine-3.10

# $ docker build . -t lf/alpine:conda

# 添加标签说明
LABEL author="lfwolf" email="38577695@qq.com"  purpose="docker for [http://zh.gluon.ai]"

# 配置清华镜像地址
# RUN echo "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.8/main/" > /etc/apk/repositories
# 替换阿里云的源
RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories
RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories

# 配置工作目录
WORKDIR /data

# 配置Volueme挂载点 "/data"
VOLUME [ "/data" ]

# 设置用户
USER root

# 设置时区变量
ENV TIME_ZONE Asia/Shanghai

#安装时区包并配置时区TIME_ZONE为中国标准时间
RUN apk add --no-cache -U tzdata \
    && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \ 
    && echo "${TIME_ZONE}" > /etc/timezone 

# 更新升级软件
RUN apk add --update --upgrade \
    vim 
    #gcc \
    #g++ \

#安装openssh 并修改配置文件
RUN apk add --no-cache openssh-server && \
    sed -i "s/#PermitRootLogin.*/PermitRootLogin yes/g" /etc/ssh/sshd_config && \
    ssh-keygen -t rsa -P "" -f /etc/ssh/ssh_host_rsa_key && \
    ssh-keygen -t ecdsa -P "" -f /etc/ssh/ssh_host_ecdsa_key && \
    ssh-keygen -t ed25519 -P "" -f /etc/ssh/ssh_host_ed25519_key && \
    echo "root:admin" | chpasswd

# 安装软件python3,升级pip,setuptools,安装selenium
# RUN apk add --no-cache python3 \
#    #&& apk add --no-cache python3-dev \
#    && python3 -m ensurepip \
#    && rm -r /usr/lib/python*/ensurepip \
#    && pip3 install --default-timeout=100 --no-cache-dir --upgrade pip \
#    && pip3 install --default-timeout=100 --no-cache-dir --upgrade setuptools \
#    && pip3 install --default-timeout=100 --no-cache-dir --upgrade selenium \
#    && if [ ! -e /usr/bin/pip ]; then ln -s pip3 /usr/bin/pip ; fi \
#    && if [[ ! -e /usr/bin/python ]]; then ln -sf /usr/bin/python3 /usr/bin/python; fi \
#    && rm -rf /var/cache/apk/* \
#    && rm -rf ~/.cache/pip

# 安装glibc-2.29
# RUN apk add --no-cache bash bzip2-dev  --allow-untrusted ca-certificates ;\
#    wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub;\
#    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-2.29-r0.apk;\
#    apk add glibc-2.29-r0.apk

# Install conda
ENV CONDA_DIR="/opt/conda"
ENV PATH="$CONDA_DIR/bin:$PATH"
RUN CONDA_VERSION="4.5.12" && \
    CONDA_MD5_CHECKSUM="866ae9dff53ad0874e1d1a60b1ad1ef8" && \
    \
    apk add --no-cache --virtual=.build-dependencies wget ca-certificates bash && \
    \
    mkdir -p "$CONDA_DIR" && \
    wget "http://repo.continuum.io/miniconda/Miniconda3-${CONDA_VERSION}-Linux-x86_64.sh" -O miniconda.sh && \
    echo "$CONDA_MD5_CHECKSUM  miniconda.sh" | md5sum -c && \
    bash miniconda.sh -f -b -p "$CONDA_DIR" && \
    echo "export PATH=$CONDA_DIR/bin:\$PATH" > /etc/profile.d/conda.sh && \
    rm miniconda.sh && \
    \
    conda update --all --yes && \
    conda config --set auto_update_conda False && \
    rm -r "$CONDA_DIR/pkgs/" && \
    \
    apk del --purge .build-dependencies && \
    \
    mkdir -p "$CONDA_DIR/locks" && \
    chmod 777 "$CONDA_DIR/locks"

# Copy the current directory contents into the container at /app
COPY ./d2l-zh.zip /data/
RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple && \
    unzip d2l-zh.zip && \
    conda env update -f environment.yml && \
    rm d2l-zh.zip


# 设置启动点 镜像启动时的第一个命令, 通常 docker run 的参数不会覆盖掉该指令
# ENTRYPOINT [ "/bin/sh" ]

# 配置非生效对外端口
EXPOSE 22

# 设置启动时预期的命令参数, 可以被 docker run 的参数覆盖掉.
# CMD [ "/bin/sh" ]

# 生成镜像 docker build -t lf-alpine:conda  .

# fail. 运行失败