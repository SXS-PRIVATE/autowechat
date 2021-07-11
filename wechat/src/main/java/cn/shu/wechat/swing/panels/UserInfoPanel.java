package cn.shu.wechat.swing.panels;


import cn.shu.wechat.beans.pojo.Contacts;
import cn.shu.wechat.core.Core;
import cn.shu.wechat.swing.app.Launcher;
import cn.shu.wechat.swing.components.Colors;
import cn.shu.wechat.swing.components.GBC;
import cn.shu.wechat.swing.components.RCButton;
import cn.shu.wechat.swing.components.VerticalFlowLayout;
import cn.shu.wechat.swing.db.model.ContactsUser;
import cn.shu.wechat.swing.db.model.Room;
import cn.shu.wechat.swing.db.service.ContactsUserService;
import cn.shu.wechat.swing.db.service.RoomService;
import cn.shu.wechat.swing.frames.MainFrame;
import cn.shu.wechat.swing.utils.AvatarUtil;
import cn.shu.wechat.swing.utils.FontUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by song on 2017/6/15.
 */
@Data
public class UserInfoPanel extends ParentAvailablePanel
{
    private JPanel contentPanel;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private RCButton button;
    private String userId;
    private String username;
    private RoomService roomService = Launcher.roomService;
    private ContactsUserService contactsUserService = Launcher.contactsUserService;

    public UserInfoPanel(JPanel parent)
    {
        super(parent);
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents()
    {
        contentPanel = new JPanel();
        contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, 0, 20, true, false));

        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(AvatarUtil.createOrLoadUserAvatar("song").getScaledInstance(100,100, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);

        nameLabel = new JLabel();
        nameLabel.setText("Song");
        nameLabel.setFont(FontUtil.getDefaultFont(20));

        button = new RCButton("发消息", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        button.setBackground(Colors.PROGRESS_BAR_START);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(FontUtil.getDefaultFont(16));

    }

    private void initView()
    {
        this.setLayout(new GridBagLayout());

        JPanel avatarNamePanel = new JPanel();
        avatarNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        avatarNamePanel.add(imageLabel, BorderLayout.WEST);
        avatarNamePanel.add(nameLabel, BorderLayout.CENTER);

        //add(avatarNamePanel, new GBC(0,0).setAnchor(GBC.CENTER).setWeight(1,1).setInsets(0,0,0,0));
        //add(button, new GBC(0,1).setAnchor(GBC.CENTER).setWeight(1,1).setInsets(0,0,0,0));
        contentPanel.add(avatarNamePanel);
        contentPanel.add(button);

        add(contentPanel, new GBC(0,0).setWeight(1,1).setAnchor(GBC.CENTER).setInsets(0,0,250,0));
    }

    public void setUsername(String username)
    {
        this.username = username;
        nameLabel.setText(username);

        ImageIcon icon = new ImageIcon(AvatarUtil.createOrLoadUserAvatar(username).getScaledInstance(100,100, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
    }

    private void setListeners()
    {
        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                openOrCreateDirectChat();
                super.mouseClicked(e);
            }
        });
    }

    private void openOrCreateDirectChat()
    {
        JSONObject user = Core.getMemberMap().get(userId);
        /*ContactsUser user  = contactsUserService.find("username", username).get(0);*/
        Contacts contacts = JSON.parseObject(JSON.toJSONString(user), Contacts.class);
        if (!Core.getRecentContacts().contains(contacts)){
            // 房间bu存在，直接打开，否则发送请求创建房间
            createDirectChat(contacts);
        }
        ChatPanel.getContext().enterRoom(contacts.getUsername());
    }

    /**
     * 创建直接聊天
     *
     * @param contacts
     */
    private void createDirectChat(Contacts contacts)
    {
       // JOptionPane.showMessageDialog(MainFrame.getContext(), "发起聊天", "发起聊天", JOptionPane.INFORMATION_MESSAGE);
        RoomsPanel.getContext().addRoom(contacts,"");
        Core.getRecentContacts().add(contacts);

    }



}